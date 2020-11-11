import HttpException from "./HttpException";

class TokenNotFoundException extends HttpException {
    constructor() {
        super(
            401,
            'token-not-found',
            'You do not provide a token.'
        );
    }
}

export default TokenNotFoundException;
