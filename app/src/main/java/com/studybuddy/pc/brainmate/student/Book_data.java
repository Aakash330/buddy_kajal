package com.studybuddy.pc.brainmate.student;

public class Book_data {
    private String folder;

    private String cover;

    private Pages[] pages;

    private String name;

    public String getFolder ()
    {
        return folder;
    }

    public void setFolder (String folder)
    {
        this.folder = folder;
    }

    public String getCover ()
    {
        return cover;
    }

    public void setCover (String cover)
    {
        this.cover = cover;
    }

    public Pages[] getPages ()
    {
        return pages;
    }

    public void setPages (Pages[] pages)
    {
        this.pages = pages;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [folder = "+folder+", cover = "+cover+", pages = "+pages+", name = "+name+"]";
    }
}
