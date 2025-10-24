package seedu.duke.command;

import seedu.duke.container.LookUpTable;
import seedu.duke.exception.FinanceProPlusException;
import seedu.duke.meeting.MeetingList;

public class ForecastCommand extends Command {
    private String arguments;

    public ForecastCommand(String subtype) {
        this.arguments = subtype;
    }

    @Override
    public void execute(LookUpTable lookUpTable) throws FinanceProPlusException {
        if (!arguments.equals("meeting")) {
            throw new FinanceProPlusException("Forecast is only available for meetings");
        }
        MeetingList meetings = (MeetingList) lookUpTable.getList("meeting");
        meetings.listForecast();
    }

    @Override
    public void printExecutionMessage() {
        System.out.println("----------------------------------------------------");
    }
}
