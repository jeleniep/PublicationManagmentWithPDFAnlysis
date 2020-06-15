import HttpException from "./HttpException";

class InvalidInstanceUrlException extends HttpException {
    constructor() {
        super(
            403,
            'invalid-instance-url',
            'The instance url you provided is not an url'
        );
    }
}

export default InvalidInstanceUrlException;
