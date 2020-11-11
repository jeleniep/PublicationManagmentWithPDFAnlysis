import HttpException from "./HttpException";

class InvalidTokenException extends HttpException {
    constructor() {
        super(
            403,
            'invalid-token',
            'You provided an invalid token.'
        );
    }
}

export default InvalidTokenException;
