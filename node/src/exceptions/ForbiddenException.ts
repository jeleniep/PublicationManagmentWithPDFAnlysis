import HttpException from "./HttpException";

class ForbiddenException extends HttpException {
    constructor() {
        super(
            403,
            'forbidden',
            'Access forbidden'
        );
    }
}

export default ForbiddenException;
