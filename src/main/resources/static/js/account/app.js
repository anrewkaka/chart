var stompClient = null;

function connect() {
  var socket = new SockJS('/bittrex-websocket');
  stompClient = Stomp.over(socket);
  stompClient.debug = null;
  stompClient.connect({}, function(frame) {
    stompClient.subscribe('/topic/account_profit', function(account) {
      showAccount(JSON.parse(account.body).body);
    });
  });
}

function showAccount(message) {
  var tbody = $("#profit").find("tbody");
  tbody.find("tr").remove();
  var profit = JSON.parse(message);
  $(profit).each(
      function(index, data) {
        var row = $("<tr></tr>");
        row.attr("id", "profit_row_" + index);
        row.attr("data-toggle", "modal");
        row.attr("data-target", "#myModal");
        row.addClass(getCssClass(data.profit_in_percentage));
        row.addClass("profit_row");
        row.append($("<td></td>").addClass("profit_name").text(data.name));
        row.append($("<td></td>").addClass("hidden-xs").text(data.amount));
        row.append($("<td></td>").addClass("hidden-xs").text(data.buy_price));
        row.append($("<td></td>").addClass("hidden-xs").text(data.current_price));
        row.append($("<td></td>").text(data.profit));
        row.append($("<td></td>").addClass("profit_percentage").text(
            $.number(data.profit_in_percentage, 2) + " %"));
        tbody.append(row);
      });
}

$(function() {
  connect();
  setInterval(send, 500);

  $("table tbody tr").on("click", function() {
    alert("test");
  });
});

function send() {
  stompClient.send("/app/account_profit", {}, "BTC");
}

function getCssClass(profitInPercentage) {
  var cssClass = "";
  if (profitInPercentage < -4) {
    cssClass = "danger";
  } else if (-4 < profitInPercentage < 4) {
    cssClass = "warning";
  } else if (4 < profitInPercentage) {
    cssClass = "success";
  }
  return cssClass;
}

function showModal() {
  var name = $(this).find(".profit_name").html();
  var profitInPercentage = $(this).find(".profit_percentage").html();
  alert(name);
  var modal = $("#myModal");
  $(modal).find("#model-title").html(name);
  // $(modal).modal('show');
}

function sell(name) {

}
