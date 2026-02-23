import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { usePokemonData } from '../hooks/usePokemonData';

function ChoosePokemon() {
    const {
        loading,
        pokemonListDetails,
    } = usePokemonData();

    const [selected, setSelected] = useState(null);
    const navigate = useNavigate();

    // Play cry sound on button click
    const playSound = (soundUrl) => {
        if (!soundUrl) return;
        const audio = new Audio(soundUrl);
        audio.play().catch(e => console.log("Audio play failed:", e));
    };

    const handleStartBattle = () => {
        if (!selected) {
            alert('Please select a Pokémon!');
            return;
        }

        // ✅ FIX: navigate to /battle (your battle page)
        navigate('/battle', { state: { playerPokemon: selected } });
    };

    if (loading) return <p style={{ textAlign: 'center', padding: '20px' }}>Loading Pokémon...</p>;

    return (
        <div style={{ textAlign: 'center', padding: '20px', backgroundColor: '#1e1e1e', minHeight: '100vh', color: 'white' }}>
            <h2>Choose Your Pokémon</h2>
            <div style={{ display: 'flex', flexWrap: 'wrap', justifyContent: 'center', gap: '20px', marginTop: '20px' }}>
                {(pokemonListDetails || []).map((pokemon) => (
                    <div
                        key={pokemon.id}
                        onClick={() => setSelected(pokemon)}
                        style={{
                            border: selected?.id === pokemon.id ? '3px solid gold' : '1px solid #444',
                            borderRadius: '12px',
                            padding: '15px',
                            cursor: 'pointer',
                            width: '160px',
                            backgroundColor: selected?.id === pokemon.id ? '#333' : '#2a2a2a',
                            boxShadow: selected?.id === pokemon.id ? '0 0 12px rgba(255,215,0,0.6)' : 'none',
                            transition: 'all 0.2s ease',
                        }}
                    >
                        <img
                            src={
                                pokemon.sprites?.other?.['official-artwork']?.front_default ||
                                pokemon.sprites?.front_default ||
                                'https://dummyimage.com/150x150/555/fff&text=No+Image'
                            }
                            alt={pokemon.name}
                            style={{ width: '100%', height: 'auto' }}
                        />
                        <h3 style={{ textTransform: 'capitalize', margin: '10px 0 5px' }}>{pokemon.name}</h3>

                        <span
                            style={{
                                backgroundColor: getTypeColor(pokemon.type),
                                padding: '4px 8px',
                                borderRadius: '12px',
                                fontSize: '0.85rem',
                                color: 'white',
                            }}
                        >
                            {pokemon.type}
                        </span>

                        <div style={{ marginTop: '10px', textAlign: 'left' }}>
                            <div style={{ display: 'flex', justifyContent: 'space-between', fontSize: '0.9rem' }}>
                                <span>HP</span>
                                <span>{pokemon.hp}/{pokemon.maxHp}</span>
                            </div>
                            <div
                                style={{
                                    height: '8px',
                                    backgroundColor: '#444',
                                    borderRadius: '4px',
                                    overflow: 'hidden',
                                    marginTop: '4px',
                                }}
                            >
                                <div
                                    style={{
                                        width: `${(pokemon.hp / pokemon.maxHp) * 100}%`,
                                        height: '100%',
                                        backgroundColor: getColorByHp(pokemon.hp / pokemon.maxHp),
                                        transition: 'width 0.3s ease',
                                    }}
                                />
                            </div>
                        </div>

                        {/* Sound button */}
                        {pokemon.sound && (
                            <button
                                onClick={(e) => {
                                    e.stopPropagation(); // prevent card select
                                    playSound(pokemon.sound);
                                }}
                                style={{
                                    marginTop: '10px',
                                    padding: '6px 12px',
                                    fontSize: '0.9rem',
                                    borderRadius: '6px',
                                    border: 'none',
                                    cursor: 'pointer',
                                    backgroundColor: '#ffcc00',
                                    color: '#000',
                                }}
                            >
                                🔊 Play Cry
                            </button>
                        )}
                    </div>
                ))}
            </div>

            <button
                onClick={handleStartBattle}
                disabled={!selected}
                style={{
                    marginTop: '30px',
                    padding: '12px 30px',
                    fontSize: '1.2rem',
                    backgroundColor: selected ? '#ffcc00' : '#555',
                    color: selected ? '#000' : '#aaa',
                    border: 'none',
                    borderRadius: '8px',
                    cursor: selected ? 'pointer' : 'not-allowed',
                }}
            >
                Start Battle!
            </button>
        </div>
    );
}

// Utility: Get color by type
const getTypeColor = (type) => {
    const colors = {
        fire: '#f08030',
        water: '#6890f0',
        grass: '#78c850',
        electric: '#f8d030',
        ice: '#98d8d8',
        fighting: '#c03028',
        poison: '#a040a0',
        ground: '#e0c068',
        flying: '#a890f0',
        psychic: '#f85888',
        bug: '#a8b820',
        rock: '#b8a038',
        ghost: '#705898',
        dragon: '#7038f8',
        dark: '#705848',
        steel: '#b8b8d0',
        fairy: '#ee99ac',
        normal: '#a8a878',
    };
    return colors[type] || '#666';
};

// Utility: Get HP bar color
const getColorByHp = (ratio) => {
    if (ratio > 0.5) return '#4caf50'; // green
    if (ratio > 0.2) return '#ff9800'; // orange
    return '#f44336'; // red
};

export default ChoosePokemon;


