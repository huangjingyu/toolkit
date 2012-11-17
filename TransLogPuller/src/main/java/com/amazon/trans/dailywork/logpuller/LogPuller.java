package com.amazon.trans.dailywork.logpuller;

public interface LogPuller {
    public void pull(ProgramArgs args) throws Exception;
}
