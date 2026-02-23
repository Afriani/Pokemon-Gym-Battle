import {useNavigate} from 'react-router-dom';
import "../styles/home.css"; // Make sure to create this CSS file

function Home() {
    const navigate = useNavigate();

    return (
        <div className="home-background">
            <div className="content-box">
                <h1>Pokemon Gym Battle</h1>
                <h3>Hi, welcome, Do you want to start your battle?</h3>
            </div>

            {/* This container puts buttons side-by-side */}
            <div className="button-group">
                <button className="btn yes-btn" onClick={() => navigate('/choose')}>Yes</button>
                <button className="btn no-btn" onClick={() => alert('Come back soon!')}>No</button>
            </div>

        </div>
    );
}

export default Home;