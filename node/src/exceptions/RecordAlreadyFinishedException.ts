import HttpException from "./HttpException";

class RecordAlreadyFinishedException extends HttpException {
    constructor() {
        super(
            400,
            'already-finished',
            'This recording was already finished'
        );
    }
}

export default RecordAlreadyFinishedException;
