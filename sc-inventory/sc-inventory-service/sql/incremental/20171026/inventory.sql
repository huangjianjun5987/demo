#更新tran_data表
UPDATE sc185.tran_data
SET taxes    = round(total_cost / (units * (1 + vat_rate * 0.01)) * vat_rate * 0.01, 2),
  total_cost = round(total_cost / (1 + vat_rate * 0.01), 2)
WHERE tran_code = '20'
      AND vat_rate IS NOT NULL;
#更新item_loc_soh
UPDATE sc185.item_loc_soh
SET av_cost = av_cost / (1 + 17 * 0.01);