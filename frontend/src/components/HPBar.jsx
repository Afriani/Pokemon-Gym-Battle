import { useEffect, useState } from "react";

export default function HPBar({ current, max }) {
    const pct = max > 0 ? Math.max(0, Math.round((current / max) * 100)) : 0;

    const [flash, setFlash] = useState(false);
    const [prevPct, setPrevPct] = useState(pct);

    // Detect critical hit (large HP drop)
    useEffect(() => {
        if (pct < prevPct - 20) {
            setFlash(true);
            setTimeout(() => setFlash(false), 500);
        }
    }, [pct, prevPct]);

    // Update previous percentage AFTER detection
    useEffect(() => {
        setPrevPct(pct);
    }, [pct]);

    const color =
        pct > 50 ? "#4caf50" :
            pct > 20 ? "#ffb300" :
                "#f44336";

    return (
        <div className={`hpbar ${flash ? "hp-flash" : ""} ${pct < 20 ? "hp-low" : ""}`}>
            <div className="hpbar-track">
                <div
                    className="hpbar-fill"
                    style={{
                        width: `${pct}%`,
                        background: color,
                        transition: "width 600ms ease"
                    }}
                />
            </div>
            <div className="hp-text">{current} / {max}</div>
        </div>
    );
}


