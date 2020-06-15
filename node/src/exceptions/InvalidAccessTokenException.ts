import HttpException from "./HttpException";

class InvalidAccessTokenException extends HttpException {
    constructor() {
        super(
            403,
            'invalid-access-token',
            'You provided an invalid access token for this instance'
        );
    }
}

export default InvalidAccessTokenException;
