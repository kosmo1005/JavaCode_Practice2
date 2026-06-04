package com.cool.java_core.atm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ATM {
    Map <String, List<Nominal>> memory;
    public ATM() {
        memory = new HashMap<String, List<Nominal>>();
    }

    public int issueAmount (Request req){
        int remainingAmount = req.amount;
        int amountIssued = 0;
        if (memory.containsKey(req.currency)){
            List<Nominal> nominalsList = memory.get(req.currency);
            // идем по корзинам с номиналами
            for (Nominal nominal : nominalsList){
                // если оставшаяся сумма меньше корзины с номиналом, скипаем шаг
                if(remainingAmount < nominal.nominal){
                    continue;
                } else {
                    // сколько купюр мы сможем выдать из этой корзины
                    int countOfNominalInReq = remainingAmount/nominal.nominal;
                    // если общая сумма в корзине больше или равна оставшейся
                    if (nominal.count * nominal.nominal >= remainingAmount){
                        // уменьшаем количество купюр в корзине
                        nominal.count = nominal.count - countOfNominalInReq;
                        // уменьшаем запрашиваемую сумму и увеличиваем выдачу на количество купюр * номинал
                        remainingAmount -= countOfNominalInReq * nominal.nominal;
                        amountIssued += countOfNominalInReq * nominal.nominal;
                    } else {
                        // выдаем сколько есть
                        remainingAmount -= nominal.count * nominal.nominal;
                        amountIssued += nominal.count * nominal.nominal;
                        nominal.count = 0;
                    }
                }
            }
        }

        return amountIssued;
    }
}
