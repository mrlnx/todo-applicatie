package nl.justid.todobackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TodoBackend {

	public static void main(String[] args) {
		SpringApplication.run(TodoBackend.class, args);
	}

    @RequestMapping("/")
    public ResponseEntity<String> mainRoot() {
        return ResponseEntity.ok("Hello TODO backend applicatie");
    }
}
