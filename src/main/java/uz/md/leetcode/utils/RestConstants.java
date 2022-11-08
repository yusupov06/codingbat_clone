package uz.md.leetcode.utils;



public interface RestConstants {

    String INITIAL_FILTERING_FUNCTION = "" +
            "DROP FUNCTION IF EXISTS get_query_result;" +
            " create function get_query_result(sql_query text) " +
            "    returns TABLE(id character varying, name character varying, phone_number character varying, order_count integer, enabled boolean) " +
            "    language plpgsql " +
            "as " +
            "$$ " +
            "BEGIN " +
            "    RETURN QUERY " +
            "        EXECUTE sql_query; " +
            "END " +
            "$$;";

}
