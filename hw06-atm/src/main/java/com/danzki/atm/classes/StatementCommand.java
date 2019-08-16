package com.danzki.atm.classes;

import com.danzki.atm.Cellable;
import com.danzki.atm.Command;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StatementCommand implements Command {
    private Cellable cell;
    private int nominal;

    @Override
    public int execute() {
        return cell.getStatement(nominal);
    }
}
