import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Link, useNavigate, Navigate } from 'react-router-dom';
import { Leaf, LogIn, UserPlus, LogOut, User, Activity, AlertCircle } from 'lucide-react';

const API_URL = import.meta.env.VITE_API_URL || 'http://localhost:38472';
const apiUrl = (path) => `${API_URL}${path}`;


function App() {
  const [user, setUser] = useState(() => {
    const saved = sessionStorage.getItem('user');
    return saved ? JSON.parse(saved) : null;
  });

  React.useEffect(() => {
    if (user) sessionStorage.setItem('user', JSON.stringify(user));
    else sessionStorage.removeItem('user');
  }, [user]);

  return (
    <Router>
      <div className="min-h-screen">
        <header className="container">
          <nav className="navbar">
            <Link to="/" className="brand">
              <Leaf size={28} />
              Food Waste Reduction
            </Link>
            <div className="flex-center" style={{ gap: '1rem' }}>
              {user ? (
                <>
                  <span style={{ fontWeight: 500, color: 'var(--text-muted)' }}>Welcome, {user.username}</span>
                  <button className="btn btn-outline" onClick={() => setUser(null)} style={{ padding: '0.5rem 1rem', width: 'auto' }}>
                    <LogOut size={18} />
                    Logout
                  </button>
                </>
              ) : (
                <>
                  <Link to="/login" className="btn btn-outline" style={{ width: 'auto', padding: '0.5rem 1rem' }}>Login</Link>
                  <Link to="/register" className="btn btn-primary" style={{ width: 'auto', padding: '0.5rem 1rem' }}>Register</Link>
                </>
              )}
            </div>
          </nav>
        </header>

        <main className="container animate-fade-in">
          <Routes>
            <Route path="/" element={user ? <Navigate to="/dashboard" /> : <LandingPage />} />
            <Route path="/login" element={<Login setUser={setUser} />} />
            <Route path="/register" element={<Register />} />
            <Route path="/dashboard" element={user ? <Dashboard user={user} /> : <Navigate to="/login" />} />
          </Routes>
        </main>
      </div>
    </Router>
  );
}

function LandingPage() {
  return (
    <div className="flex-center flex-col" style={{ textAlign: 'center', padding: '4rem 0', maxWidth: '800px', margin: '0 auto' }}>
      <div style={{ background: 'var(--primary)', color: 'white', padding: '1rem', borderRadius: '50%', marginBottom: '1.5rem', display: 'inline-block' }}>
        <Leaf size={64} />
      </div>
      <h1 style={{ fontSize: '3rem', marginBottom: '1rem', lineHeight: '1.2' }}>Save Food. Save the Planet.</h1>
      <p style={{ fontSize: '1.25rem', color: 'var(--text-muted)', marginBottom: '2.5rem' }}>
        Join our platform connecting restaurants, NGOs, and volunteers to reduce food waste and feed those in need.
      </p>
      <div className="flex-center" style={{ gap: '1rem', width: '100%', maxWidth: '400px' }}>
        <Link to="/register" className="btn btn-primary" style={{ fontSize: '1.125rem', padding: '1rem' }}>Get Started</Link>
      </div>
    </div>
  );
}

function Login({ setUser }) {
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleLogin = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);
    
    try {
      const res = await fetch(apiUrl('/api/users/login'), {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password })
      });
      
      const data = await res.json();
      
      if (res.ok) {
        setUser(data);
        navigate('/dashboard');
      } else {
        setError(data.error || 'Invalid credentials');
      }
    } catch (err) {
      setError('Could not connect to the server. Is it running?');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex-center" style={{ padding: '2rem 0' }}>
      <div className="glass-panel" style={{ width: '100%', maxWidth: '400px', padding: '2rem' }}>
        <div style={{ textAlign: 'center', marginBottom: '2rem' }}>
          <div style={{ background: '#ecfdf5', color: 'var(--primary)', padding: '1rem', borderRadius: '50%', display: 'inline-block', marginBottom: '1rem' }}>
            <LogIn size={32} />
          </div>
          <h2>Welcome Back</h2>
          <p style={{ color: 'var(--text-muted)' }}>Sign in to your account</p>
        </div>
        
        <form onSubmit={handleLogin}>
          {error && (
            <div style={{ background: '#fef2f2', color: 'var(--danger)', padding: '0.75rem', borderRadius: '8px', marginBottom: '1rem', display: 'flex', alignItems: 'center', gap: '0.5rem', fontSize: '0.875rem' }}>
              <AlertCircle size={16} />
              {error}
            </div>
          )}
          <div className="form-group">
            <label className="form-label">Email</label>
            <input type="email" required className="form-input" value={email} onChange={e => setEmail(e.target.value)} placeholder="you@example.com" />
          </div>
          <div className="form-group">
            <label className="form-label">Password</label>
            <input type="password" required className="form-input" value={password} onChange={e => setPassword(e.target.value)} placeholder="••••••••" />
          </div>
          <button type="submit" className="btn btn-primary" disabled={loading} style={{ marginTop: '1rem' }}>
            {loading ? 'Signing in...' : 'Sign In'}
          </button>
        </form>
      </div>
    </div>
  );
}

function Register() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({ username: '', email: '', password: '', phone: '', role: 'USER', address: '' });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);
    
    try {
      const res = await fetch(apiUrl('/api/users/register'), {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData)
      });
      
      const data = await res.json();
      
      if (res.ok) {
        navigate('/login');
      } else {
        setError(data.error || 'Registration failed');
      }
    } catch (err) {
      setError('Could not connect to the server. Is it running?');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex-center" style={{ padding: '1rem 0' }}>
      <div className="glass-panel" style={{ width: '100%', maxWidth: '500px', padding: '2rem' }}>
        <div style={{ textAlign: 'center', marginBottom: '1.5rem' }}>
          <div style={{ background: '#ecfdf5', color: 'var(--primary)', padding: '1rem', borderRadius: '50%', display: 'inline-block', marginBottom: '1rem' }}>
            <UserPlus size={32} />
          </div>
          <h2>Create an Account</h2>
          <p style={{ color: 'var(--text-muted)' }}>Join the movement against food waste</p>
        </div>
        
        <form onSubmit={handleSubmit}>
          {error && (
            <div style={{ background: '#fef2f2', color: 'var(--danger)', padding: '0.75rem', borderRadius: '8px', marginBottom: '1rem', display: 'flex', alignItems: 'center', gap: '0.5rem', fontSize: '0.875rem' }}>
              <AlertCircle size={16} />
              {error}
            </div>
          )}
          
          <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '1rem' }}>
            <div className="form-group">
              <label className="form-label">Username</label>
              <input type="text" required className="form-input" value={formData.username} onChange={e => setFormData({...formData, username: e.target.value})} />
            </div>
            <div className="form-group">
              <label className="form-label">Phone</label>
              <input type="text" required className="form-input" value={formData.phone} onChange={e => setFormData({...formData, phone: e.target.value})} />
            </div>
          </div>
          
          <div className="form-group">
            <label className="form-label">Email</label>
            <input type="email" required className="form-input" value={formData.email} onChange={e => setFormData({...formData, email: e.target.value})} />
          </div>
          
          <div className="form-group">
            <label className="form-label">Password</label>
            <input type="password" required className="form-input" value={formData.password} onChange={e => setFormData({...formData, password: e.target.value})} minLength={6} />
          </div>
          
          <div className="form-group">
            <label className="form-label">Role</label>
            <select className="form-input" value={formData.role} onChange={e => setFormData({...formData, role: e.target.value})}>
              <option value="USER">Standard User</option>
              <option value="RESTAURANT">Restaurant / Donor</option>
              <option value="NGO">NGO / Charity</option>
              <option value="VOLUNTEER">Volunteer</option>
            </select>
          </div>
          
          <div className="form-group">
            <label className="form-label">Address</label>
            <input type="text" required className="form-input" value={formData.address} onChange={e => setFormData({...formData, address: e.target.value})} />
          </div>
          
          <button type="submit" className="btn btn-primary" disabled={loading} style={{ marginTop: '1rem' }}>
            {loading ? 'Creating account...' : 'Create Account'}
          </button>
        </form>
      </div>
    </div>
  );
}

function Dashboard({ user }) {
  const [isEditing, setIsEditing] = useState(false);
  const [isDonating, setIsDonating] = useState(false);
  
  // Edit Profile State
  const [editData, setEditData] = useState({ username: user.username, phone: user.phone, address: user.address });
  
  // Donation State
  const [donationData, setDonationData] = useState({
    foodItem: '',
    quantity: '',
    unit: 'kg',
    expirationDate: ''
  });
  
  const [message, setMessage] = useState('');
  
  // NGO Feed State
  const [donationsFeed, setDonationsFeed] = useState([]);
  const [claimingId, setClaimingId] = useState(null);
  const [searchQuery, setSearchQuery] = useState('');
  const [sortBy, setSortBy] = useState('newest');
  
  // NGO Claim History State
  const [ngoClaimedDonations, setNgoClaimedDonations] = useState([]);
  
  // Restaurant Feed State
  const [restaurantDonations, setRestaurantDonations] = useState([]);
  
  // Delivery Timing State
  const [deliveryTimeInput, setDeliveryTimeInput] = useState({});
  
  React.useEffect(() => {
    const fetchDonations = () => {
      if (user.role === 'NGO') {
        fetch(apiUrl('/api/donations'))
          .then(res => res.json())
          .then(data => setDonationsFeed(data))
          .catch(err => console.error(err));
          
        fetch(apiUrl(`/api/donations/ngo/${user.userId}`))
          .then(res => res.json())
          .then(data => setNgoClaimedDonations(data))
          .catch(err => console.error(err));
      } else if (user.role === 'RESTAURANT') {
        fetch(apiUrl(`/api/donations/restaurant/${user.userId}`))
          .then(res => res.json())
          .then(data => setRestaurantDonations(data))
          .catch(err => console.error(err));
      }
    };
    
    fetchDonations();
    const interval = setInterval(fetchDonations, 5000);
    return () => clearInterval(interval);
  }, [user.role, user.userId]);

  const handleEditSubmit = async (e) => {
    e.preventDefault();
    // For now, just simulate success since we need an update profile endpoint
    setMessage('Profile updated successfully! (Simulation)');
    setIsEditing(false);
    setTimeout(() => setMessage(''), 3000);
  };

  const handleDonationSubmit = async (e) => {
    e.preventDefault();
    
    // Expiry Date Validation
    if (new Date(donationData.expirationDate) <= new Date()) {
      setMessage('Expiration date must be in the future!');
      return;
    }
    
    // Simulate backend call for now until we build the backend endpoint
    try {
      const res = await fetch(apiUrl('/api/donations'), {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ ...donationData, restaurantId: user.userId })
      });
      
      if (res.ok) {
        setMessage('Donation posted successfully!');
        setIsDonating(false);
        setDonationData({ foodItem: '', quantity: '', unit: 'kg', expirationDate: '' });
        // Fetch updated donations to show it immediately
        fetch(apiUrl(`/api/donations/restaurant/${user.userId}`))
          .then(r => r.json())
          .then(data => setRestaurantDonations(data));
      } else {
        setMessage('Backend not ready yet. Donation form simulated successfully!');
        setIsDonating(false);
      }
    } catch (err) {
      setMessage('Backend not ready yet. Donation form simulated successfully!');
      setIsDonating(false);
    }
    setTimeout(() => setMessage(''), 4000);
  };

  const handleClaim = async (donationId) => {
    setClaimingId(donationId);
    try {
      const res = await fetch(apiUrl(`/api/donations/${donationId}/claim`), {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ ngoId: user.userId })
      });
      if (res.ok) {
        setDonationsFeed(donationsFeed.filter(d => d.donationId !== donationId));
        setMessage('Donation claimed successfully! The restaurant has been notified.');
        
        // Refresh the claim history
        fetch(apiUrl(`/api/donations/ngo/${user.userId}`))
          .then(res => res.json())
          .then(data => setNgoClaimedDonations(data));
      } else {
        setMessage('Failed to claim donation. It might have already been claimed.');
      }
    } catch (err) {
      setMessage('Network error while claiming donation.');
    }
    setClaimingId(null);
    setTimeout(() => setMessage(''), 4000);
  };

  const handleApprove = async (donationId) => {
    const deliveryTime = deliveryTimeInput[donationId] || '';
    if (!deliveryTime.trim()) {
      setMessage('Please enter an estimated delivery/pickup time before approving.');
      return;
    }
    try {
      const res = await fetch(apiUrl(`/api/donations/${donationId}/approve`), {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ deliveryTime })
      });
      if (res.ok) {
        setMessage('Arrangement approved successfully!');
        setRestaurantDonations(restaurantDonations.map(d => 
          d.donationId === donationId ? { ...d, status: 'APPROVED', deliveryTimeEstimate: deliveryTime } : d
        ));
        setDeliveryTimeInput({ ...deliveryTimeInput, [donationId]: '' });
      } else {
        setMessage('Failed to approve arrangement.');
      }
    } catch (err) {
      setMessage('Network error while approving.');
    }
    setTimeout(() => setMessage(''), 4000);
  };

  const filteredAndSortedDonations = React.useMemo(() => {
    let result = [...donationsFeed];
    
    if (searchQuery.trim() !== '') {
      const q = searchQuery.toLowerCase();
      result = result.filter(d => 
        (d.foodItem && d.foodItem.toLowerCase().includes(q)) ||
        (d.restaurantName && d.restaurantName.toLowerCase().includes(q)) ||
        (d.restaurantAddress && d.restaurantAddress.toLowerCase().includes(q))
      );
    }
    
    if (sortBy === 'expiringSoonest') {
      result.sort((a, b) => new Date(a.expirationDate) - new Date(b.expirationDate));
    } else if (sortBy === 'largestQuantity') {
      result.sort((a, b) => b.quantity - a.quantity);
    } else {
      // newest
      result.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt));
    }
    
    return result;
  }, [donationsFeed, searchQuery, sortBy]);

  return (
    <div>
      <div className="flex-between" style={{ marginBottom: '2rem', alignItems: 'flex-start' }}>
        <div>
          <h2 style={{ fontSize: '2rem', margin: 0, marginBottom: '1rem' }}>Dashboard</h2>
          {user.role === 'RESTAURANT' && !isDonating && (
            <button className="btn btn-primary" style={{ width: 'auto' }} onClick={() => setIsDonating(true)}>
              + Donate Food
            </button>
          )}
        </div>
        
        <div className="stat-card" style={{ width: '350px', padding: '1.25rem', marginBottom: 0 }}>
          <div style={{ display: 'flex', alignItems: 'center', gap: '1rem', marginBottom: '1rem' }}>
            <div style={{ background: '#ecfdf5', color: 'var(--primary)', padding: '0.75rem', borderRadius: '12px' }}>
              <User size={24} />
            </div>
            <div>
              <h3 style={{ margin: 0, fontSize: '1.125rem', textTransform: 'none', letterSpacing: 'normal' }}>Profile Information</h3>
              <span style={{ color: 'var(--text-muted)', fontSize: '0.75rem', textTransform: 'uppercase', letterSpacing: '0.05em' }}>{user.role}</span>
            </div>
          </div>
          
          {isEditing ? (
            <form onSubmit={handleEditSubmit} style={{ marginTop: '1rem' }}>
              <div className="form-group">
                <input type="text" className="form-input" placeholder="Username" value={editData.username} onChange={e => setEditData({...editData, username: e.target.value})} style={{ padding: '0.5rem' }} />
              </div>
              <div className="form-group">
                <input type="text" className="form-input" placeholder="Phone" value={editData.phone} onChange={e => setEditData({...editData, phone: e.target.value})} style={{ padding: '0.5rem' }} />
              </div>
              <div className="form-group">
                <input type="text" className="form-input" placeholder="Address" value={editData.address} onChange={e => setEditData({...editData, address: e.target.value})} style={{ padding: '0.5rem' }} />
              </div>
              <div className="flex-between" style={{ gap: '0.5rem' }}>
                <button type="submit" className="btn btn-primary" style={{ padding: '0.5rem 1rem', fontSize: '0.875rem' }}>Save</button>
                <button type="button" className="btn btn-outline" onClick={() => setIsEditing(false)} style={{ padding: '0.5rem 1rem', fontSize: '0.875rem' }}>Cancel</button>
              </div>
            </form>
          ) : (
            <>
              <div style={{ display: 'flex', flexDirection: 'column', gap: '0.5rem', marginTop: '0.5rem', fontSize: '0.875rem' }}>
                <div className="flex-between">
                  <span style={{ color: 'var(--text-muted)' }}>Email</span>
                  <span style={{ fontWeight: 500 }}>{user.email}</span>
                </div>
                <div className="flex-between">
                  <span style={{ color: 'var(--text-muted)' }}>Phone</span>
                  <span style={{ fontWeight: 500 }}>{user.phone}</span>
                </div>
                <div className="flex-between">
                  <span style={{ color: 'var(--text-muted)' }}>Address</span>
                  <span style={{ fontWeight: 500 }}>{user.address}</span>
                </div>
              </div>
              <button className="btn btn-outline" style={{ marginTop: '1rem', width: '100%', padding: '0.5rem', fontSize: '0.875rem' }} onClick={() => setIsEditing(true)}>Edit Profile</button>
            </>
          )}
        </div>
      </div>
      
      {message && (
        <div style={{ background: '#ecfdf5', color: 'var(--primary)', padding: '1rem', borderRadius: '8px', marginBottom: '2rem' }}>
          {message}
        </div>
      )}

      {isDonating && user.role === 'RESTAURANT' && (
        <div className="glass-panel" style={{ padding: '2rem', marginBottom: '2rem' }}>
          <h3 style={{ marginBottom: '1.5rem' }}>Post a Food Donation</h3>
          <form onSubmit={handleDonationSubmit}>
            <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '1rem' }}>
              <div className="form-group">
                <label className="form-label">Food Item</label>
                <input type="text" required className="form-input" placeholder="e.g. 5 boxes of pasta" value={donationData.foodItem} onChange={e => setDonationData({...donationData, foodItem: e.target.value})} />
              </div>
              <div className="form-group">
                <label className="form-label">Expiration Date</label>
                <input type="datetime-local" required className="form-input" value={donationData.expirationDate} onChange={e => setDonationData({...donationData, expirationDate: e.target.value})} />
              </div>
            </div>
            
            <div style={{ display: 'grid', gridTemplateColumns: '2fr 1fr', gap: '1rem' }}>
              <div className="form-group">
                <label className="form-label">Quantity</label>
                <input type="number" required className="form-input" min="1" value={donationData.quantity} onChange={e => setDonationData({...donationData, quantity: e.target.value})} />
              </div>
              <div className="form-group">
                <label className="form-label">Unit</label>
                <select className="form-input" value={donationData.unit} onChange={e => setDonationData({...donationData, unit: e.target.value})}>
                  <option value="kg">kg</option>
                  <option value="lbs">lbs</option>
                  <option value="meals">meals</option>
                  <option value="items">items</option>
                </select>
              </div>
            </div>
            
            <div className="flex-center" style={{ gap: '1rem', marginTop: '1rem', justifyContent: 'flex-start' }}>
              <button type="submit" className="btn btn-primary" style={{ width: 'auto' }}>Post Donation</button>
              <button type="button" className="btn btn-outline" style={{ width: 'auto' }} onClick={() => setIsDonating(false)}>Cancel</button>
            </div>
          </form>
        </div>
      )}
      
      <div style={{ display: 'grid', gridTemplateColumns: '1fr', gap: '2rem', marginBottom: '2rem' }}>
        
        {user.role === 'NGO' ? (
          <div className="stat-card" style={{ gridColumn: 'span 1' }}>
            <div style={{ display: 'flex', alignItems: 'center', gap: '1rem', marginBottom: '1rem' }}>
              <div style={{ background: '#eff6ff', color: '#3b82f6', padding: '0.75rem', borderRadius: '12px' }}>
                <Activity size={24} />
              </div>
              <div>
                <h3 style={{ margin: 0, fontSize: '1.25rem' }}>Available Donations Feed</h3>
              </div>
            </div>
            
            <div style={{ display: 'flex', gap: '1rem', marginBottom: '1rem' }}>
              <input 
                type="text" 
                className="form-input" 
                placeholder="Search food, restaurant, or location..." 
                value={searchQuery}
                onChange={e => setSearchQuery(e.target.value)}
                style={{ flex: 1 }}
              />
              <select 
                className="form-input" 
                value={sortBy}
                onChange={e => setSortBy(e.target.value)}
                style={{ width: '200px' }}
              >
                <option value="newest">Newest Added</option>
                <option value="expiringSoonest">Expiring Soonest</option>
                <option value="largestQuantity">Largest Quantity</option>
              </select>
            </div>
            
            <div style={{ display: 'flex', flexDirection: 'column', gap: '1rem', marginTop: '1rem', maxHeight: '500px', overflowY: 'auto', paddingRight: '0.5rem' }}>
              {filteredAndSortedDonations.length === 0 ? (
                <p style={{ color: 'var(--text-muted)', fontStyle: 'italic', textAlign: 'center', padding: '2rem 0' }}>No available donations match your search.</p>
              ) : (
                filteredAndSortedDonations.map((donation, idx) => (
                  <div key={idx} style={{ border: '1px solid var(--border)', borderRadius: '12px', padding: '1rem', background: '#f8fafc' }}>
                    <div className="flex-between" style={{ marginBottom: '0.5rem' }}>
                      <h4 style={{ margin: 0, color: 'var(--primary)', fontSize: '1.125rem' }}>{donation.foodItem}</h4>
                      <span style={{ background: '#dcfce7', color: '#166534', padding: '0.25rem 0.5rem', borderRadius: '999px', fontSize: '0.75rem', fontWeight: 600 }}>
                        {donation.quantity} {donation.unit}
                      </span>
                    </div>
                    
                    <div style={{ fontSize: '0.875rem', color: 'var(--text-muted)', marginBottom: '1rem' }}>
                      Expires: {new Date(donation.expirationDate).toLocaleString()}
                    </div>
                    
                    <div style={{ borderTop: '1px solid var(--border)', paddingTop: '0.75rem', fontSize: '0.875rem' }}>
                      <strong>{donation.restaurantName || 'Unknown Restaurant'}</strong>
                      <div style={{ marginTop: '0.25rem', color: 'var(--text-muted)' }}>📍 {donation.restaurantAddress}</div>
                      <div style={{ marginTop: '0.25rem', color: 'var(--text-muted)' }}>📞 {donation.restaurantPhone}</div>
                    </div>
                    
                    <button 
                      className="btn btn-primary" 
                      style={{ marginTop: '1rem', padding: '0.5rem', width: '100%' }}
                      onClick={() => handleClaim(donation.donationId)}
                      disabled={claimingId === donation.donationId}
                    >
                      {claimingId === donation.donationId ? 'Claiming...' : 'Contact / Claim'}
                    </button>
                  </div>
                ))
              )}
            </div>
            
            <div style={{ display: 'flex', alignItems: 'center', gap: '1rem', marginBottom: '1rem', marginTop: '2rem' }}>
              <div style={{ background: '#dcfce7', color: '#166534', padding: '0.75rem', borderRadius: '12px' }}>
                <Activity size={24} />
              </div>
              <div>
                <h3 style={{ margin: 0, fontSize: '1.25rem' }}>My Claimed Donations</h3>
              </div>
            </div>
            
            <div style={{ display: 'flex', flexDirection: 'column', gap: '1rem', marginTop: '1rem', maxHeight: '500px', overflowY: 'auto', paddingRight: '0.5rem' }}>
              {ngoClaimedDonations.length === 0 ? (
                <p style={{ color: 'var(--text-muted)', fontStyle: 'italic', textAlign: 'center', padding: '2rem 0' }}>You haven't claimed any donations yet.</p>
              ) : (
                ngoClaimedDonations.map((donation, idx) => (
                  <div key={idx} style={{ border: '1px solid var(--border)', borderRadius: '12px', padding: '1rem', background: '#f8fafc' }}>
                    <div className="flex-between" style={{ marginBottom: '0.5rem' }}>
                      <h4 style={{ margin: 0, color: 'var(--primary)', fontSize: '1.125rem' }}>{donation.foodItem}</h4>
                      <span style={{ 
                        background: donation.status === 'APPROVED' ? '#dcfce7' : '#fef3c7', 
                        color: donation.status === 'APPROVED' ? '#166534' : '#92400e', 
                        padding: '0.25rem 0.5rem', borderRadius: '999px', fontSize: '0.75rem', fontWeight: 600 
                      }}>
                        {donation.status === 'APPROVED' ? 'APPROVED - Ready for Hand-off!' : 'WAITING FOR RESTAURANT RESPONSE'}
                      </span>
                    </div>
                    
                    <div style={{ fontSize: '0.875rem', color: 'var(--text-muted)', marginBottom: '1rem' }}>
                      Quantity: {donation.quantity} {donation.unit}
                    </div>
                    
                    <div style={{ borderTop: '1px solid var(--border)', paddingTop: '0.75rem', fontSize: '0.875rem' }}>
                      <strong>{donation.restaurantName}</strong>
                      <div style={{ marginTop: '0.25rem', color: 'var(--text-muted)' }}>📍 {donation.restaurantAddress}</div>
                      <div style={{ marginTop: '0.25rem', color: 'var(--text-muted)' }}>📞 {donation.restaurantPhone}</div>
                      
                      {donation.status === 'APPROVED' && donation.deliveryTimeEstimate && (
                        <div style={{ marginTop: '1rem', background: '#dcfce7', padding: '0.75rem', borderRadius: '8px', color: '#166534', fontWeight: 500 }}>
                          ⏱️ Estimated Time: {donation.deliveryTimeEstimate}
                        </div>
                      )}
                    </div>
                  </div>
                ))
              )}
            </div>
          </div>
        ) : user.role === 'RESTAURANT' ? (
          <div className="stat-card" style={{ gridColumn: 'span 1' }}>
            <div style={{ display: 'flex', alignItems: 'center', gap: '1rem', marginBottom: '1rem' }}>
              <div style={{ background: '#eff6ff', color: '#3b82f6', padding: '0.75rem', borderRadius: '12px' }}>
                <Activity size={24} />
              </div>
              <div>
                <h3 style={{ margin: 0, fontSize: '1.25rem' }}>My Donations Activity</h3>
              </div>
            </div>
            
            <div style={{ display: 'flex', flexDirection: 'column', gap: '1rem', marginTop: '1rem', maxHeight: '500px', overflowY: 'auto', paddingRight: '0.5rem' }}>
              {restaurantDonations.length === 0 ? (
                <p style={{ color: 'var(--text-muted)', fontStyle: 'italic', textAlign: 'center', padding: '2rem 0' }}>You haven't posted any donations yet.</p>
              ) : (
                restaurantDonations.map((donation, idx) => (
                  <div key={idx} style={{ border: '1px solid var(--border)', borderRadius: '12px', padding: '1rem', background: '#f8fafc' }}>
                    <div className="flex-between" style={{ marginBottom: '0.5rem' }}>
                      <h4 style={{ margin: 0, color: 'var(--primary)', fontSize: '1.125rem' }}>{donation.foodItem}</h4>
                      <span style={{ 
                        background: donation.status === 'CLAIMED' ? '#dcfce7' : '#fef3c7', 
                        color: donation.status === 'CLAIMED' ? '#166534' : '#92400e', 
                        padding: '0.25rem 0.5rem', borderRadius: '999px', fontSize: '0.75rem', fontWeight: 600 
                      }}>
                        {donation.status}
                      </span>
                    </div>
                    
                    <div style={{ fontSize: '0.875rem', color: 'var(--text-muted)', marginBottom: '1rem' }}>
                      Quantity: {donation.quantity} {donation.unit}
                    </div>
                    
                    {donation.status === 'CLAIMED' ? (
                      <div style={{ borderTop: '1px solid #dcfce7', paddingTop: '0.75rem', fontSize: '0.875rem', background: '#f0fdf4', padding: '0.75rem', borderRadius: '8px', marginTop: '0.5rem' }}>
                        <strong style={{ color: '#166534' }}>Claimed by: {donation.ngoName}</strong>
                        <div style={{ marginTop: '0.25rem', color: '#166534' }}>📍 {donation.ngoAddress}</div>
                        <div style={{ marginTop: '0.25rem', color: '#166534' }}>📞 {donation.ngoPhone}</div>
                        
                        <div style={{ marginTop: '1rem' }}>
                          <input 
                            type="text" 
                            className="form-input" 
                            placeholder="e.g. Today at 5:00 PM" 
                            style={{ marginBottom: '0.5rem', background: 'white' }}
                            value={deliveryTimeInput[donation.donationId] || ''}
                            onChange={(e) => setDeliveryTimeInput({ ...deliveryTimeInput, [donation.donationId]: e.target.value })}
                          />
                          <button 
                            className="btn btn-primary" 
                            style={{ padding: '0.5rem', width: '100%', background: '#16a34a' }}
                            onClick={() => handleApprove(donation.donationId)}
                          >
                            Approve & Confirm Arrangement
                          </button>
                        </div>
                      </div>
                    ) : donation.status === 'APPROVED' ? (
                      <div style={{ borderTop: '1px solid #dcfce7', paddingTop: '0.75rem', fontSize: '0.875rem', background: '#f0fdf4', padding: '0.75rem', borderRadius: '8px', marginTop: '0.5rem' }}>
                        <strong style={{ color: '#166534' }}>✓ Approved for: {donation.ngoName}</strong>
                        <div style={{ marginTop: '0.25rem', color: '#166534' }}>📍 {donation.ngoAddress}</div>
                        <div style={{ marginTop: '0.25rem', color: '#166534' }}>📞 {donation.ngoPhone}</div>
                        
                        {donation.deliveryTimeEstimate && (
                          <div style={{ marginTop: '0.75rem', fontWeight: 600, color: '#166534' }}>
                            ⏱️ Estimated: {donation.deliveryTimeEstimate}
                          </div>
                        )}
                      </div>
                    ) : (
                      <div style={{ borderTop: '1px solid var(--border)', paddingTop: '0.75rem', fontSize: '0.875rem', color: 'var(--text-muted)' }}>
                        Waiting for an NGO to claim...
                      </div>
                    )}
                  </div>
                ))
              )}
            </div>
          </div>
        ) : (
          <div className="stat-card">
            <div style={{ display: 'flex', alignItems: 'center', gap: '1rem', marginBottom: '1rem' }}>
              <div style={{ background: '#eff6ff', color: '#3b82f6', padding: '0.75rem', borderRadius: '12px' }}>
                <Activity size={24} />
              </div>
              <div>
                <h3 style={{ margin: 0, fontSize: '1.25rem' }}>Recent Activity</h3>
              </div>
            </div>
            <div style={{ display: 'flex', flexDirection: 'column', gap: '1rem', marginTop: '1rem' }}>
              <p style={{ color: 'var(--text-muted)', fontStyle: 'italic', textAlign: 'center', padding: '1rem 0' }}>No recent activity to display.</p>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

export default App;
