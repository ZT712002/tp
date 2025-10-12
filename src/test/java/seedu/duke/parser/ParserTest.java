package seedu.duke.parser;

import org.junit.jupiter.api.Test;
import seedu.duke.exception.FinanceProPlusException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParserTest {

    @Test
    void splitCommand_userInputWithArgs_returnsTwoParts() {
        String userInput = "client add";
        String[] commandParts = Parser.splitCommand(userInput);
        assertArrayEquals(new String[]{"client", "add"}, commandParts);
    }

    @Test
    void splitCommand_userInputWithoutArgs_returnsOnePart() {
        String userInput = "list";
        String[] commandParts = Parser.splitCommand(userInput);
        assertArrayEquals(new String[]{"list"}, commandParts);
    }

    @Test
    void selectParserType_validListCommand_returnsListParser() throws FinanceProPlusException {
        Parser parser = Parser.selectParserType("list");
        assertInstanceOf(ListParser.class, parser);
    }

    @Test
    void selectParserType_validExitCommand_returnsExitParser() throws FinanceProPlusException {
        Parser parser = Parser.selectParserType("exit");
        assertInstanceOf(ExitParser.class, parser);
    }

    @Test
    void selectParserType_validPolicyCommand_returnsPolicyParser() throws FinanceProPlusException {
        Parser parser = Parser.selectParserType("policy add");
        assertInstanceOf(PolicyParser.class, parser);
    }

    @Test
    void selectParserType_invalidCommand_throwsException() {
        String invalidInput = "unknownCommand";
        Exception exception = assertThrows(FinanceProPlusException.class, () -> {
            Parser.selectParserType(invalidInput);
        });
        assertEquals("Invalid command type", exception.getMessage());
    }

    @Test
    void parse_invalidInput_throwsException() {
        String invalidInput = "someInvalidCommand";
        Exception exception = assertThrows(FinanceProPlusException.class, () -> {
            Parser.parse(invalidInput);
        });
        assertEquals("Invalid command type", exception.getMessage());
    }




}
