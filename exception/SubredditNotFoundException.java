package com.redditclone.project.exception;

public class SubredditNotFoundException extends Throwable{
    public SubredditNotFoundException(String subredditName) {super(subredditName);
    }
}
