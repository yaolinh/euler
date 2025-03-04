package nampnguyen.app.practices.projecteuler.common.model;

import nampnguyen.app.practices.projecteuler.model.Result;

public abstract class Solution<T> {
    public abstract Result<T> solve();
}
