import HttpException from "./HttpException";

class NoAccessTokenException extends HttpException {
    constructor() {
        super(
            401,
            'no-access-token',
            `No access token was provided to the request`
        );
    }
}

export default NoAccessTokenException;
