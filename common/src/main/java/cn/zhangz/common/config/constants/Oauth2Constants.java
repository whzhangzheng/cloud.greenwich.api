package cn.zhangz.common.config.constants;

public interface Oauth2Constants {

    String oauth2_username = "username";

    String oauth2_password = "password";

    String oauth2_clint_id = "client_id";

    String oauth2_clint_secret = "client_secret";

    String oauth2_grant_type = "grant_type";

    String oauth2_grant_type_client = "client_credentials";

    String oauth2_token_result_key = "access_token";

    String oauth2_token_result_error_key = "error_description";

    String oauth2_scope = "scope";

    String root_scope = "root";

    String authorities = "authorities";

    String check_token_url = "/oauth/check_token";

    String get_token_url = "/oauth/token";
}
