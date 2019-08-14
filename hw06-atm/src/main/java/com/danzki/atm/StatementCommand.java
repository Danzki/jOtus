package com.danzki.atm;

import com.danzki.atm.interfaces.Command;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StatementCommand implements Command {
    private Cell cell;
    private int nominal;

    @Override
    public int execute() {
        return cell.getStatement(nominal);
    }
}
