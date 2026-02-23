import { useEffect, useRef } from "react";

export default function BattleLog({ logs = [] }) {
    const ref = useRef();

    useEffect(() => {
        if (ref.current) {
            ref.current.scrollTop = ref.current.scrollHeight;
        }
    }, [logs]);

    return (
        <div className="battle-log" ref={ref} role="log" aria-live="polite">
            {logs.map((l, i) => (
                <div key={i} className="log-line">
                    {l.message}
                </div>
            ))}
        </div>
    );
}


