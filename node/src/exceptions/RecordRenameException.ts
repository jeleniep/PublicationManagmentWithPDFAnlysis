import HttpException from "./HttpException";

class RecordRenameException extends HttpException {
    constructor() {
        super(
            500,
            'rename-error',
            'Rename error appeared on the backend - record does not have a filename'
        );
    }
}

export default RecordRenameException;
