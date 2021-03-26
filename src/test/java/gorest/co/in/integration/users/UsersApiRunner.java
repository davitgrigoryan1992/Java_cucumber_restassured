package gorest.co.in.integration.users;

import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions.SnippetType;


@RunWith(Cucumber.class)
@CucumberOptions(features= "src/test/java/resources/features/users/UsersAPI.feature",
        glue ={"gorest.co.in.integration.users"},
        monochrome = true,
        snippets = SnippetType.CAMELCASE,
        tags = {"@user"}
//        , tags = {"@scenario1"}
)

public class UsersApiRunner {

}
