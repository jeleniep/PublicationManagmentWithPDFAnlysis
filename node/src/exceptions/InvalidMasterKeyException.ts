import HttpException from "./HttpException";

class InvalidMasterKeyException extends HttpException {
    constructor() {
        super(
            403,
            'invalid-master-key',
            'You failed to provide a correct master key'
        );
    }
}

export default InvalidMasterKeyException;
