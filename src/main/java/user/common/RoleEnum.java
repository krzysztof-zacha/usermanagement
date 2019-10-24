package user.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum RoleEnum {
    @JsonProperty("ADMIN")
    ADMIN,

    @JsonProperty("USER")
    USER,

    @JsonProperty("MODERATOR")
    MODERATOR
}
