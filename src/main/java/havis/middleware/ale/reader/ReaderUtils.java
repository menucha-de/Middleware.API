package havis.middleware.ale.reader;

import havis.middleware.ale.base.operation.tag.Field;
import havis.middleware.ale.base.operation.tag.Operation;
import havis.middleware.ale.base.operation.tag.result.ReadResult;
import havis.middleware.ale.base.operation.tag.result.Result;
import havis.middleware.ale.base.operation.tag.result.ResultState;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ReaderUtils {

    /**
     * Converts the reader read results to new read result list depending on
     * operations. Moves and cuts result data if necessary. Last bits will not
     * blanked if length is not a round lot of 8
     *
     * @param results
     *            Reader read result for each bank
     * @param operations
     *            Requested read operations
     * @return Single read result for each operation depending on fielddata
     */
    public static Map<Integer, Result> toResult(ReadResult[] results,
            Operation[] operations) {
    	return toResult(results, Arrays.asList(operations));
    }

    /**
     * Converts the reader read results to new read result list depending on
     * operations. Moves and cuts result data if necessary. Last bits will not
     * blanked if length is not a round lot of 8
     *
     * @param results
     *            Reader read result for each bank
     * @param operations
     *            Requested read operations
     * @return Single read result for each operation depending on fielddata
     */
    public static Map<Integer, Result> toResult(ReadResult[] results,
            Iterable<Operation> operations) {
        Map<Integer, Result> dict = new HashMap<Integer, Result>();
        for (Operation operation : operations) {
            // get bank read result
            ReadResult result;
            if ((operation.getField().getBank() < results.length)
                    && ((result = results[operation.getField().getBank()]) instanceof ReadResult)) {
                if ((result.getState() == ResultState.SUCCESS)) {
                    if ((result.getState() == ResultState.SUCCESS)
                            && (result.getData() != null)
                            && (result.getData().length * 8 >= operation
                                    .getField().getOffset()
                                    + operation.getField().getLength())) {
                        dict.put(Integer.valueOf(operation.getId()), toResult(result, operation));
                    } else {
                        result = new ReadResult();
                        result.setState(ResultState.FIELD_NOT_FOUND_ERROR);
                        dict.put(Integer.valueOf(operation.getId()), result);
                    }
                } else {
                    dict.put(Integer.valueOf(operation.getId()),
                            new ReadResult(result.getState()));
                }
            } else {
                dict.put(Integer.valueOf(operation.getId()), new ReadResult(
                        ResultState.FIELD_NOT_FOUND_ERROR));
            }
        }
        return dict;
    }

    /**
     * Returns the target result for single a reader operation
     *
     * @param result
     *            The source result
     * @param operation
     *            The operation
     * @return The target result
     */
    public static Result toResult(ReadResult result, Operation operation) {
        if (result.getState() == ResultState.SUCCESS) {
            int length;
            byte[] data;
            Field field = operation.getField();
            // number of bits to move within a byte
            int offset = field.getOffset() % 8;
            // number of bytes of the result data
            length = field.getLength() == 0 ? result.getData().length
                    - field.getOffset() / 8 : (field.getLength() + (8 - field
                    .getLength() % 8) % 8) / 8;
            data = new byte[length];
            // skip bytes depending on field offset
            int j = field.getOffset() / 8;
            byte[] bytes = result.getData();
            for (int i = 0; i < length; i++) {
                // move current byte offset bits left and
                // move to next byte, if offset greater then zero move next byte
                // (8 - offset) bits right and add up
                data[i] = (byte) (((bytes[j++] & 0xFF) << offset) + (j < bytes.length
                        && offset > 0 ? (bytes[j] & 0xFF) >> (8 - offset) : 0));
            }
            if (field.getLength() % 8 != 0) {
                int current = data[data.length - 1] & 0xFF;
                current &= 0xFF << (8 - (field.getLength() % 8));
                data[data.length - 1] = (byte) current;
            }
            // add read result with data and result code
            ReadResult r = new ReadResult();
            r.setData(data);
            r.setState(result.getState());
            return r;
        } else {
            ReadResult r = new ReadResult();
            r.setData(new byte[] {});
            r.setState(result.getState());
            return r;
        }
    }
}
