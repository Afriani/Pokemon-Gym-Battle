import { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate, useLocation } from "react-router-dom";

import PokemonCard from "../components/PokemonCard";
import MoveButtons from "../components/MoveButtons";
import BattleLog from "../components/BattleLog";
import trophy from "../assets/noto--trophy.png";
import lost from "../assets/skull.png"

import "../styles/battle.css";

let uniqueId = 0;

export default function BattleScreen({ initialState }) {
    const [state, setState] = useState(initialState);
    const [busy, setBusy] = useState(false);

    const location = useLocation();
    const selectedPokemon = location.state?.playerPokemon;

    const navigate = useNavigate();
    const [showReplayPrompt, setShowReplayPrompt] = useState(false);

    // Create one single audio instance to reuse
    const globalAudioPlayer = new Audio();

    // Unique ID generator (fix duplicate key warnings)
    function getId() {
        return uniqueId++;
    }

    // Animation states
    const [playerShake, setPlayerShake] = useState(false);
    const [opponentShake, setOpponentShake] = useState(false);
    const [damagePopups, setDamagePopups] = useState([]);
    const [attackAnimations, setAttackAnimations] = useState([]);

    // KO animation states
    const [playerKO, setPlayerKO] = useState(false);
    const [opponentKO, setOpponentKO] = useState(false);

    const moveSounds = {
        // 🔥 Fire
        "Flamethrower": "https://play.pokemonshowdown.com/audio/sfx/flamethrower.mp3",
        "Fire Blast": "https://play.pokemonshowdown.com/audio/sfx/fireblast.mp3",
        "Ember": "https://play.pokemonshowdown.com/audio/sfx/ember.mp3",

        // ⚡ Electric
        "Thunderbolt": "https://play.pokemonshowdown.com/audio/sfx/thunderbolt.mp3",
        "Thunder": "https://play.pokemonshowdown.com/audio/sfx/thunder.mp3",
        "Thunder Shock": "https://play.pokemonshowdown.com/audio/sfx/thundershock.mp3",

        // 💧 Water
        "Water Gun": "https://play.pokemonshowdown.com/audio/sfx/watergun.mp3",
        "Hydro Pump": "https://play.pokemonshowdown.com/audio/sfx/hydropump.mp3",
        "Surf": "https://play.pokemonshowdown.com/audio/sfx/surf.mp3",

        // 🌿 Grass
        "Vine Whip": "https://play.pokemonshowdown.com/audio/sfx/vinewhip.mp3",
        "Razor Leaf": "https://play.pokemonshowdown.com/audio/sfx/razorleaf.mp3",

        // 🦷 Normal / Physical
        "Tackle": "https://play.pokemonshowdown.com/audio/sfx/tackle.mp3",
        "Quick Attack": "https://play.pokemonshowdown.com/audio/sfx/quickattack.mp3",
        "Body Slam": "https://play.pokemonshowdown.com/audio/sfx/bodyslam.mp3",
        "Hyper Beam": "https://play.pokemonshowdown.com/audio/sfx/hyperbeam.mp3",
        "Pound": "https://play.pokemonshowdown.com/audio/sfx/pound.mp3",
        "Scratch": "https://play.pokemonshowdown.com/audio/sfx/scratch.mp3",
    };

    // Turn indicator
    const [turnText, setTurnText] = useState("Your turn!");

    // Victory / Defeat screen
    const [battleResult, setBattleResult] = useState(null);

    // Play Pokémon cry
    function playCry(url) {
        if (!url) return;
        console.log("Playing Cry:", url);
        // We use a separate one for cries so they can overlap with move sounds
        const cryPlayer = new Audio(url);
        cryPlayer.volume = 0.5;
        cryPlayer.play().catch(e => console.error("Cry Playback Failed:", e));
    }

    function playSound(url) {
        if (!url) return;
        console.log("Playing Sound:", url);
        globalAudioPlayer.src = url;
        globalAudioPlayer.volume = 0.4;
        globalAudioPlayer.play().catch(e => console.error("Global Playback Failed:", e));
    }

    // Fetch Pokémon ID from PokéAPI (for sprites)
    async function fetchPokemonId(name) {
        const res = await fetch(`https://pokeapi.co/api/v2/pokemon/${name.toLowerCase()}`);
        const data = await res.json();
        return data.id;
    }

    // Load initial battle state
    useEffect(() => {
        if (!initialState) {
            (async () => {
                const chosen = selectedPokemon;

                const res = await axios.post("/api/battle/start", {
                    name: chosen.name,
                    type: chosen.type,
                    // maxHp: chosen.maxHp,
                    // hp: chosen.hp
                });


                const battle = res.data;

                // Add sprites for PLAYER
                battle.player.sprites = {
                    front_default: chosen.sprites.front_default,
                    back_default: chosen.sprites.back_default
                };


                // Add sprites for OPPONENT
                const opponentId = await fetchPokemonId(battle.opponent.name);
                battle.opponent.sprites = {
                    front_default: `https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${opponentId}.png`,
                    back_default: `https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/${opponentId}.png`
                };

                setState(battle);
            })();
        }
    }, [initialState]);

    useEffect(() => {
        const unlock = () => {
            const audio = new Audio();
            audio.play().then(() => {
                audio.pause();
                audio.currentTime = 0;
                console.log("Audio Context Unlocked!");
            }).catch(e => console.log("Unlock failed", e));

            window.removeEventListener('click', unlock);
        };
        window.addEventListener('click', unlock);
        return () => window.removeEventListener('click', unlock);
    }, []);

    // Create attack animation object
    function triggerAttackAnimation(type, attacker) {
        const id = getId();
        setAttackAnimations(prev => [...prev, { id, type, attacker }]);

        setTimeout(() => {
            setAttackAnimations(prev => prev.filter(a => a.id !== id));
        }, 800);
    }

    // Handle move selection
    async function onMove(moveName) {
        if (busy || state?.finished) return;

        // 🔊 Use playSound here! (This fixes the ESLint error)
        const moveSoundUrl = moveSounds[moveName];
        if (moveSoundUrl) {
            playSound(moveSoundUrl);
        }

        setBusy(true);
        setTurnText("Opponent's turn…");

        try {
            const res = await axios.post("/api/battle/attack", {
                battleId: state.battleId,
                move: moveName,
                difficulty: "normal"
            });

            const newState = res.data;

            // Re-add sprites (backend does not store them)
            const opponentId = await fetchPokemonId(newState.opponent.name);
            newState.opponent.sprites = {
                front_default: `https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${opponentId}.png`,
                back_default: `https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/${opponentId}.png`
            };

            newState.player.sprites = {
                front_default: selectedPokemon.sprites.front_default,
                back_default: selectedPokemon.sprites.back_default
            };

            // Calculate damage
            const pDamage = state.player.currentHp - newState.player.currentHp;
            const oDamage = state.opponent.currentHp - newState.opponent.currentHp;

            const attackTypes = ["fireball", "lightning", "water", "leaf", "shadow", "beam"];
            const randomType = attackTypes[Math.floor(Math.random() * attackTypes.length)];

            // Handle Opponent taking damage
            if (oDamage > 0) {
                setOpponentShake(true);
                setDamagePopups(prev => [...prev, { id: getId(), amount: oDamage, target: "opponent" }]);

                const oppId = await fetchPokemonId(newState.opponent.name);

                // Delay the cry to make it feel reactive
                setTimeout(() => {
                    const cryUrl = `https://raw.githubusercontent.com/PokeAPI/cries/main/cries/pokemon/latest/${oppId}.ogg`;
                    playCry(cryUrl);
                }, 250); // 300ms delay

                triggerAttackAnimation(randomType, "player");
                setTimeout(() => setOpponentShake(false), 400);
            }

            if (pDamage > 0) {
                setPlayerShake(true);
                setDamagePopups(prev => [...prev, { id: getId(), amount: pDamage, target: "player" }]);

                const plyId = await fetchPokemonId(newState.player.name);

                // Delay the cry to make it feel reactive
                setTimeout(() => {
                    const cryUrl = `https://raw.githubusercontent.com/PokeAPI/cries/main/cries/pokemon/latest/${plyId}.ogg`;
                    playCry(cryUrl);
                }, 250); // 300ms delay

                triggerAttackAnimation(randomType, "opponent");
                setTimeout(() => setPlayerShake(false), 400);
            }

            // KO detection + delayed replay prompt
            if (newState.opponent.currentHp <= 0 && !opponentKO) {
                setOpponentKO(true);
                setTurnText("Opponent fainted!"); // Update text immediately

                // Wait 1.5 seconds for the KO animation to finish before showing the result box
                setTimeout(() => {
                    setBattleResult("victory");
                    setShowReplayPrompt(true);
                }, 1500);
            }

            else if (newState.player.currentHp <= 0 && !playerKO) {
                setPlayerKO(true);
                setTurnText("Your Pokemon fainted...");

                // Wait 1.5 seconds for the KO animation to finish
                setTimeout(() => {
                    setBattleResult("defeat");
                    setShowReplayPrompt(true);
                }, 1500);
            }

            setState(newState);
            setTurnText("Your turn!");
        } catch (e) {
            console.error("Battle error:", e);
        } finally {
            setBusy(false);
        }
    }

    if (!state) return <div className="loading">Loading battle…</div>;

    return (
        <div className="battle-screen">

            <div className="turn-indicator">{turnText}</div>

            <div className="opponent-area">
                <PokemonCard pokemon={state.opponent} isOpponent />
            </div>

            <div className="field-and-log">

                <div className="field">

                    <div className="pokemon-platform opponent-platform"></div>

                    <img
                        src={state.opponent?.sprites?.front_default}
                        alt={state.opponent?.name}
                        className={`opponent-sprite ${opponentShake ? "hit-shake" : ""} ${opponentKO ? "ko-animation" : ""}`}
                    />

                    {damagePopups
                        .filter(p => p.target === "opponent")
                        .map(p => (
                            <div key={p.id} className="damage-popup" style={{ top: "60px", right: "28%" }}>
                                -{p.amount}
                            </div>
                        ))}

                    <div className="pokemon-platform player-platform"></div>

                    <img
                        src={state.player?.sprites?.back_default}
                        alt={state.player?.name}
                        className={`player-sprite ${playerShake ? "hit-shake" : ""} ${playerKO ? "ko-animation" : ""}`}
                    />

                    {damagePopups
                        .filter(p => p.target === "player")
                        .map(p => (
                            <div key={p.id} className="damage-popup" style={{ bottom: "60px", left: "28%" }}>
                                -{p.amount}
                            </div>
                        ))}

                    {attackAnimations.map(a => (
                        <div
                            key={a.id}
                            className={`attack-animation ${a.type} ${a.attacker}`}
                        ></div>
                    ))}
                </div>
            </div>



            <div className="player-area">
                <PokemonCard pokemon={state.player} />
                <MoveButtons
                    moves={state.player.moves}
                    onMove={onMove}
                    disabled={busy || state.finished}
                />
            </div>

            {/* Replay prompt */}
            {showReplayPrompt && (
                <div className={`battle-result-overlay ${battleResult}`}>
                    <div className="battle-result-box">
                        <div className="result-header">
                            {battleResult === "victory" ? (
                                <span className="result-icon">
                                    <img src={trophy}/>
                                </span>
                            ) : (
                                <span className="result-icon">
                                    <img src={lost}/>
                                </span>
                            )}
                            <h2 className="result-title">
                                {battleResult === "victory" ? "VICTORY!" : "DEFEAT..."}
                            </h2>
                        </div>

                        <p className="result-message">
                            {battleResult === "victory"
                                ? `Great job! ${state.player.name} won the battle!`
                                : `Oh no! ${state.player.name} has fainted.`}
                        </p>

                        <hr className="result-divider" />

                        <p className="replay-text">Do you want to battle again?</p>

                        <div className="replay-buttons">
                            <button className="replay-btn yes-btn" onClick={() => navigate("/choose")}>
                                YES
                            </button>
                            <button className="replay-btn no-btn" onClick={() => navigate("/")}>
                                NO
                            </button>
                        </div>
                    </div>
                </div>
            )}

            <BattleLog logs={state.logs} />

        </div>
    );
}


