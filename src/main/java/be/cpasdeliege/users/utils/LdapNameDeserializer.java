package be.cpasdeliege.users.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.ldap.support.LdapNameBuilder;

import java.io.IOException;
import javax.naming.Name;

public class LdapNameDeserializer extends JsonDeserializer<Name> {

    @Override
    public Name deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String dnString = jsonParser.getText();
        return LdapNameBuilder.newInstance(dnString).build();
    }
}
