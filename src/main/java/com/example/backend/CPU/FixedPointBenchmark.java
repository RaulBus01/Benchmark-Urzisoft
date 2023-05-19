package com.example.backend.CPU;

public class FixedPointBenchmark{
    private int workload;
    private int[] num;
    private int[] res;
    private int[] c;
    private int j, k, l;

    private boolean canceled = false;

    private void ArithmeticOPS(){ // random numbers in num
        for(int i=0; i < workload && !canceled; i++){ // => 29 OPS
            j = num[1] * (k - j) * (l - k);
            k = num[3] * k - (l - j) * k;
            l = (l - k) * (num[2] + j);
            res[1] = j + k + l;
            res[2] = j * k * l;
        }
    }

    private void BranchingOPS(){// => 11 OPS
        for(int i=0; i < workload && !canceled; i++){
            if (j == 1) {
                j = num[2];
            } else {
                j = num[3];
            }
            if (j > 2) {
                j = num[0];
            } else {
                j = num[1];
            }
            if (j < 1) {
                j = num[1];
            } else {
                j = num[0];
            }
            j = j%3;
        }
    }

    private void AssignementOPS(){ // => 34 OPS
        for(int i=0; i < workload && !canceled; i++){
            j = num[c[i%1000]%1000];
            k = res[(num[c[i%1000]])%3];
            l = res[c[num[i%1000]%1000]%3];
        }
    }

    public void run() {
        ArithmeticOPS();
        BranchingOPS();
        AssignementOPS();
    }


    public void run(Object... params) {
        if(params.length > 0 && params[0] instanceof TypeOfOperation){
            if(params[0] == TypeOfOperation.BRANCHING)
                BranchingOPS();
            else if(params[0] == TypeOfOperation.ARITHMETIC) {
                ArithmeticOPS();
            }
            else AssignementOPS();
        }
    }


    public void initialize(Object... params) {
        if(params.length > 0 && params[0] instanceof Integer){
            workload = (Integer)params[0];
        }
        num = new int[1000];
        for(int i=0; i < 1000; i++){
            num[i] = (int)(Math.random() * 1000);
        }
        res = new int[3];
        j = 1;
        k = 2;
        l = 3;
        c = new int[1000];
        for(int i=0; i < 1000; i++){
            c[i] = (int)(Math.random() * 1000);
        }
    }
    public void warmup() {
        for(int i=0; i < 2; i++){
            run();
        }
    }
    public void cancel(){
        canceled = true;
    }

}
