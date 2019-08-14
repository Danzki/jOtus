package com.danzki.atm;

import com.danzki.atm.interfaces.Command;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GiveCommand implements Command {
    private Cell cell;
    private int amount;
    private int nominal;

    @Override
    public int execute() {
        return cell.getBanknotesCount(amount, nominal);
    }
}
