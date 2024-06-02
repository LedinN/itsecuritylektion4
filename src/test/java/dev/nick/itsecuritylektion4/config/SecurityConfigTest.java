package dev.nick.itsecuritylektion4.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    public void testHomePage() throws Exception {
        mvc.perform(get("/maquina").with(httpBasic("admin","maquina")))
                .andExpect(status().isOk());
    }

    @Test
    public void testNoUser() throws Exception {
        mvc.perform(get("/maquina")).andExpect(status().isUnauthorized());
    }

}