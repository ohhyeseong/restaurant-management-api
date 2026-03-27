import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Container, Row, Col, Card, Form, Button } from 'react-bootstrap';
import api from '../api/axiosConfig';

export default function Signup() {
    const [form, setForm] = useState({ email: '', password: '', name: '', role: 'CUSTOMER' });
    const navigate = useNavigate();

    const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const endpoint = form.role === 'OWNER' ? '/users/owner-signup' : '/users/signup';
            await api.post(endpoint, {
                email: form.email,
                password: form.password,
                name: form.name
            });
            alert('회원가입이 완료되었습니다!');
            navigate('/');
        } catch (error) {
            alert('회원가입에 실패했습니다.');
        }
    };

    return (
        <Container className="mt-5">
            <Row className="justify-content-md-center">
                <Col md={6}>
                    <Card className="shadow-sm">
                        <Card.Body>
                            <h3 className="text-center mb-4">회원가입</h3>
                            <Form onSubmit={handleSubmit}>
                                <Form.Group className="mb-3">
                                    <Form.Label>이메일</Form.Label>
                                    <Form.Control type="email" name="email" onChange={handleChange} required />
                                </Form.Group>

                                <Form.Group className="mb-3">
                                    <Form.Label>비밀번호</Form.Label>
                                    <Form.Control type="password" name="password" onChange={handleChange} required />
                                </Form.Group>

                                <Form.Group className="mb-3">
                                    <Form.Label>이름</Form.Label>
                                    <Form.Control type="text" name="name" onChange={handleChange} required />
                                </Form.Group>

                                <Form.Group className="mb-4">
                                    <Form.Label>가입 유형</Form.Label>
                                    <Form.Select name="role" onChange={handleChange} value={form.role}>
                                        <option value="CUSTOMER">일반 고객</option>
                                        <option value="OWNER">점주</option>
                                    </Form.Select>
                                </Form.Group>

                                <div className="d-grid gap-2">
                                    <Button variant="success" type="submit">
                                        가입하기
                                    </Button>
                                    <Button variant="link" onClick={() => navigate('/')}>
                                        이미 계정이 있으신가요? 로그인
                                    </Button>
                                </div>
                            </Form>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
}