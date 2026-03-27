import { Navbar, Container, Nav } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { useState, useEffect } from 'react';
import api from '../api/axiosConfig';

export default function Header() {
    const navigate = useNavigate();
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    useEffect(() => {
        const token = localStorage.getItem('token');
        if (token) {
            setIsLoggedIn(true);
        } else {
            setIsLoggedIn(false);
        }
    }, []);

    const handleLogout = async () => {
            try {
                await api.post('/users/logout');
            } catch (error) {
                console.error('로그아웃 API 호출 중 오류 발생:', error);
            } finally {
                localStorage.removeItem('token');
                setIsLoggedIn(false);
                alert('로그아웃 되었습니다.');
                window.location.href = '/login';
            }
        };

    return (
        <Navbar bg="dark" variant="dark" fixed="top" className="shadow-sm">
            <Container>
                <Navbar.Brand href="/">🍽️ 사장님 든든앱</Navbar.Brand>

                <Nav className="me-auto">
                    {isLoggedIn && (
                        <>
                            <Nav.Link href="/dashboard">점주 대시보드</Nav.Link>
                            <Nav.Link href="/menus">메뉴 관리</Nav.Link>
                            <Nav.Link href="/reviews">리뷰 관리</Nav.Link>
                        </>
                    )}
                </Nav>

                <Nav>
                    {isLoggedIn ? (
                        <Nav.Link onClick={handleLogout}>
                            로그아웃
                        </Nav.Link>
                    ) : (
                        <>
                            <Nav.Link href="/login">로그인</Nav.Link>
                            <Nav.Link href="/signup">회원가입</Nav.Link>
                        </>
                    )}
                </Nav>
            </Container>
        </Navbar>
    );
}