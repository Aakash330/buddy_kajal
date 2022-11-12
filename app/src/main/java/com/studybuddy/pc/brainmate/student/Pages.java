package com.studybuddy.pc.brainmate.student;

public class Pages {
    private String file;

    private String name;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ClassPojo [file = " + file + ", name = " + name + "]";
    }
}
