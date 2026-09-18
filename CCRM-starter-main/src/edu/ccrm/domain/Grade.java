package edu.ccrm.domain;

public enum Grade {
    S(10.0), A(9.0), B(8.0), C(7.0), D(6.0), E(5.0), F(0.0);

    private final double points;
    Grade(double points) { this.points = points; }
    public double points() { return points; }

    public static Grade fromScore(double score) {
        // Simple mapping; adjust as needed
        if (score >= 90) return S;
        if (score >= 80) return A;
        if (score >= 70) return B;
        if (score >= 60) return C;
        if (score >= 50) return D;
        if (score >= 40) return E;
        return F;
    }
}
