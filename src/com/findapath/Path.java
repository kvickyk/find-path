package com.findapath;

import java.text.MessageFormat;

import static java.util.Objects.isNull;

public class Path {
    private Point tip;
    private Path body;
    private int length;

    private Path(Point tip, Path body) {
        this.tip = tip;
        this.body = body;
        this.length = body != null ? body.length + 1 : 1;
    }

    public Point getTip() {
        return tip;
    }

    public Path getBody() {
        return body;
    }

    public int getLength() {
        return length;
    }

    public boolean contains(Point p) {
        if (p.equals(tip)) {
            return true;
        } else if (isNull(body)) {
            return false;
        } else {
            return body.contains(p);
        }
    }

    public static Path newInstance(Point tip, Path body) {
        return new Path(tip, body);
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0} {1}", body != null ? body.toString() : "", tip.toString());
    }
}
