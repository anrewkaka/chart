SELECT
    EX.ID,
    STCK.NM,
    STCK.HST,
    STCK.API_KEY,
    STCK.SECRET_KEY,
    PRD.NM,
    EX.DTTM,
    EX.BUY_PRC,
    EX.QTY,
    EX.TG1,
    EX.TG2,
    EX.TG3,
    EX.LOW_PRC_SELL,
    EX.SELL_PRC,
    EX.FEE,
    EX.STS,
    EX.LGC_DEL_FLG
  FROM
    EXCHANGE EX
    INNER JOIN STOCK STCK
      ON EX.STCK_ID = STCK.ID
      AND STCK.LGC_DEL_FLG = '0'
    INNER JOIN PRODUCT PRD
      ON EX.PRD_ID = PRD.ID
      AND PRD.LGC_DEL_FLG = '0'
  WHERE
    EX.PRD_ID = '1'/*productId*/
    AND EX.LGC_DEL_FLG = '0'
