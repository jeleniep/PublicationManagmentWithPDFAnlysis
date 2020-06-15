import HttpException from "./HttpException";

class InternalServerException extends HttpException {
    constructor() {
        super(
            500,
            'internal-server-error',
            `Internal server error`
        );
    }
}

export default InternalServerException;
