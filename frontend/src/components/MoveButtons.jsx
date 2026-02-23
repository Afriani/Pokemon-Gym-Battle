export default function MoveButtons({ moves = [], onMove, disabled }) {
    return (
        <div className="move-buttons">
            {moves.map((m) => (
                <button
                    key={m.name}
                    className="move-btn"
                    onClick={() => onMove(m.name)}
                    disabled={disabled}
                    aria-label={`${m.name} ${m.power} power ${Math.round(m.accuracy * 100)}%`}
                >
                    <div className="move-name">{m.name}</div>
                    <div className="move-meta">
                        {m.power} / {Math.round(m.accuracy * 100)}%
                    </div>
                </button>
            ))}
        </div>
    );
}


