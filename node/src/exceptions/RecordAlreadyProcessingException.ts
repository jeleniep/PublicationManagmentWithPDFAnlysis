import HttpException from "./HttpException";

class RecordAlreadyProcessingException extends HttpException {
    constructor() {
        super(
            400,
            'already-processing',
            'This recording is being processed'
        );
    }
}

export default RecordAlreadyProcessingException;
