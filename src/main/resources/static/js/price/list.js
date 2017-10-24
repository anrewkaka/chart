var stompClient = null;

function connect() {
    var socket = new SockJS('/chart-websocket');
    stompClient = Stomp.over(socket);
    stompClient.debug = null;
    stompClient.connect({}, function(frame) {
        stompClient.subscribe('/topic/price', function(account) {
            showPrices(JSON.parse(account.body).body);
        });
    });
}

function showPrices(message) {
    var tbody = $("#price").find("tbody");
    tbody.find("tr").remove();
    var profit = JSON.parse(message);
    $(profit).each(
            function(index, data) {
                var row = $("<tr></tr>");
                row.append($("<td></td>").addClass("col_currency").text(
                        data.name));
                row.append($("<td></td>").text(data.price));

                var btnDelete = $("<button></button>").addClass(
                        "btn btn-danger btn_delete").html("Xóa");
                var btnAdd = $("<button></button>").addClass(
                        "btn btn-success btn_add").html("Thêm");
                btnAdd.attr("data-toggle", "modal");
                btnAdd.attr("data-target", "#myModal");
                var group = $("<div></div>").addClass("btn-group");
                group.append(btnDelete);
                group.append(btnAdd);

                row.append(group);
                tbody.append(row);
            });
}

$(function() {
    connect();
    setInterval(send, 1000);

    $("#price").find("tbody").on("click", "button", function() {
        var currency = $(this).parents("tr").find(".col_currency").html();
        var exchange = $("#exchange").val();
        var isDelete = $(this).hasClass("btn_delete");
        if (isDelete) {
            deletePrice(exchange, currency);
        } else {
            showPriceBox(exchange, currency);
        }
    });

    $("#btn_add_price").click(function() {
        var exchange = $("#lbl_exchange").html();
        var currency = $("#lbl_currency").html();
        var price = $("#myModal").find("#txt_price").val();
        addPrice(exchange, currency, price);
    });

    $("#btn_add_currency").click(function() {
        displayCurrencyBox();
        $("#myModal").find("#txt_price").val("0.00000000");
        $("#lbl_exchange").html($("#exchange").val());
    });

    $("#btn_delete_all").click(function() {
        // todo delete all
        var exchange = $("#exchange").val();
        alert("delete all currency on " + exchange);
    });

    $("#txt_currency").change(function() {
        $("#lbl_currency").html($(this).val());
    });
});

function send() {
    stompClient.send("/app/get-list", {}, $("#exchange").val());
}

function showPriceBox(exchange, currency) {
    hideCurrencyBox();
    $("#lbl_exchange").html(exchange);
    $("#lbl_currency").html(currency);
    $("#myModal").find("#txt_price").val("0.00000000");
}

function addPrice(exchange, currency, price) {
    stompClient.send("/app/price-registration", {}, JSON.stringify({ "exchange":exchange, "currency":currency, "price":price }));
}

function deletePrice(exchange, currency) {
    stompClient.send("/app/price-delete", {}, JSON.stringify({ "exchange":exchange, "currency":currency }));
}

function displayCurrencyBox() {
    $("#txt_currency").show();
    $("#lbl_currency").hide();
}

function hideCurrencyBox() {
    $("#txt_currency").hide();
    $("#lbl_currency").show();
}
