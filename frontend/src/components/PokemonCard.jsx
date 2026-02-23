import HPBar from "./HPBar.jsx";

export default function PokemonCard({ pokemon, isOpponent = false }) {
    if (!pokemon) return null;

    // Prevent crash if sprites are missing
    const sprite = isOpponent
        ? pokemon.sprites?.front_default
        : pokemon.sprites?.back_default;

    return (
        <div className={`pokemon-card ${isOpponent ? "opponent" : "player"}`}>
            <div className="pokemon-meta">
                <div className="pokemon-name">
                    {pokemon.name} <span className="level">Lv{pokemon.level}</span>
                </div>
                <HPBar current={pokemon.currentHp} max={pokemon.maxHp} />
            </div>

            <div className="sprite-wrap">
                <img
                    src={sprite || "https://via.placeholder.com/150?text=No+Sprite"}
                    alt={pokemon.name}
                    className="sprite"
                />
            </div>
        </div>
    );
}


