import HttpException from "./HttpException";

class DifferentPasswordsException extends HttpException {
    constructor() {
        super(
            400,
            'given-password-different',
            'Given passwords are different'
        );
    }
}

export default DifferentPasswordsException;
