package uz.md.leetcode.config.security;

import uz.md.leetcode.controller.interfaces.*;


public interface RestConstants {

    String DEFAULT_PAGE_NUMBER = "0";
    String DEFAULT_PAGE_SIZE = "10";
    String AUTHENTICATION_HEADER = "Authorization";
    String[] OPEN_PAGES = {
            "/*",
            AuthController.AUTH_CONTROLLER_BASE_PATH + "/**",
            SectionController.BASE_PATH + "/**",
            LanguageController.BASE_PATH + "/**",
            UserController.BASE_PATH + "/**",
            UploadController.BASE_PATH + "/load-db/*",
            UploadController.BASE_PATH + "/load-fs/*",
            SectionController.BASE_PATH + "/**",
            "/api/user/test",
            "/swagger-ui/*",
            "/v3/api-docs/*",
            "/swagger-resources/**"
    };


}
