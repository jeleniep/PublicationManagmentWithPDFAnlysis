import HttpException from "./HttpException";

class IncorrectPasswordException extends HttpException {
    constructor() {
        super(
            403,
            'incorrect-password',
            `Given password is incorrect`
        );
    }
}

export default IncorrectPasswordException;
