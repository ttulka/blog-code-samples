package com.ttulka.samples.threading;

import java.util.Random;

class ExpensiveWorker {

    public void work() {
        for (int i = 0; i < 100_000; i++) {
            Math.cbrt(
                    Math.abs(
                            Math.tan(
                                    Math.abs(
                                            Math.sin(
                                                    new Random().nextDouble()
                                            ) + new Random().nextDouble()
                                    ) + new Random().nextDouble()
                            )
                    )
            );
        }
    }
}
