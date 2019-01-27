package rs.mladost.transfer.api.rest.dto.response;

import rs.mladost.transfer.api.rest.dto.ResponseStatus;

import static rs.mladost.transfer.api.rest.dto.ResponseStatus.SUCCESSFUL;

public abstract class SuccessfulResponse implements Response {

    @Override
    public ResponseStatus getStatus() {
        return SUCCESSFUL;
    }
}
