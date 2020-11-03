truncate table tool_reimb_print_t;

insert into tool_reimb_print_t (print_state,biz_id,created_date)
select '1001' print_state,t.biz_id,t.REIMB_DATE created_date from tool_deal_record_t t
where t.YL_LOCATION_NO='430621556588'  and t.REIMB_DATE>'2020-10-27';