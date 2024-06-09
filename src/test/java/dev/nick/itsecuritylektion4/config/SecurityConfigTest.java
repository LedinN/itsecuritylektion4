package dev.nick.itsecuritylektion4.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testAdminPage() throws Exception {
        mvc.perform(get("/admin").with(httpBasic("user","password")))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testLogin() throws Exception {

        mvc.perform(MockMvcRequestBuilders.post("/register")
                .param("password","password")
                .param("email", "testuser@example.com"))
        .andExpect(status().isOk())
                .andExpect(view().name("register_success"));
    }

}