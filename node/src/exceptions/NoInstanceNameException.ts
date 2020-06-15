import HttpException from "./HttpException";

class NoInstanceNameException extends HttpException {
    constructor() {
        super(
            401,
            'no-instance-name',
            `No instance name was provided to the request`
        );
    }
}

export default NoInstanceNameException;
