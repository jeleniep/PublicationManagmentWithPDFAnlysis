import HttpException from "./HttpException";

class IncorrectCredentialsException extends HttpException {
    constructor() {
        super(
            401,
            'incorrect-credentials',
            `Given email or password is incorrect`
        );
    }
}

export default IncorrectCredentialsException;
